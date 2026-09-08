const fs = require('fs')
const { join } = require('path')
const { root, versions, treeOf } = require('./versions')

const oldVersion = versions.at(-1)

function bump (newVersion) {
  const tree = treeOf(oldVersion)
  const oldDir = join(root, tree, 'versions', oldVersion)
  const newDir = join(root, tree, 'versions', newVersion)

  if (fs.existsSync(newDir)) {
    console.warn(`New version directory already exists: ${newDir}`)
    process.exit(0)
  }

  fs.mkdirSync(newDir)
  const props = fs.readFileSync(join(oldDir, 'gradle.properties'), 'utf8')
  fs.writeFileSync(join(newDir, 'gradle.properties'), props.replace(`mc.version=${oldVersion}`, `mc.version=${newVersion}`))

  // Register the node in the tree's version list and make it the version checked into src/.
  const settingsPath = join(root, 'settings.gradle')
  let settings = fs.readFileSync(settingsPath, 'utf8')
  settings = settings.replace(`"${oldVersion}"\n`, `"${oldVersion}", "${newVersion}"\n`)
  settings = settings.replace(`vcsVersion = "${oldVersion}"`, `vcsVersion = "${newVersion}"`)
  fs.writeFileSync(settingsPath, settings)

  const controllerPath = join(root, tree, 'stonecutter.gradle')
  const controller = fs.readFileSync(controllerPath, 'utf8')
  fs.writeFileSync(controllerPath, controller.replace(`stonecutter.active '${oldVersion}'`, `stonecutter.active '${newVersion}'`))

  versions.push(newVersion)
  fs.writeFileSync(join(root, 'versions.json'), JSON.stringify(versions, null, 2) + '\n')
}

module.exports = bump
if (require.main === module) {
  const [newVersion] = process.argv.slice(2)
  if (!newVersion) {
    console.error('Usage: node newVersion.js <newVersion>')
    process.exit(1)
  }
  bump(newVersion)
}
