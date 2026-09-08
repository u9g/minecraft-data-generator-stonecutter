const fs = require('fs')
const { join } = require('path')

const root = join(__dirname, '..')
const versions = require('../versions.json')

// Stonecutter trees, one per mapping family. Each holds versions/<name>/gradle.properties.
const trees = fs.readdirSync(root).filter(d => fs.existsSync(join(root, d, 'stonecutter.gradle')))

function treeOf (version) {
  const tree = trees.find(t => fs.existsSync(join(root, t, 'versions', version)))
  if (!tree) throw new Error(`No Stonecutter tree contains version ${version}`)
  return tree
}

const projectPath = version => `:${treeOf(version)}:${version}`
const runDir = version => join(treeOf(version), 'versions', version, 'run', 'server', 'minecraft-data')

module.exports = { root, versions, trees, treeOf, projectPath, runDir }

if (require.main === module) {
  const [what, version] = process.argv.slice(2)
  if (what === 'tree') console.log(treeOf(version))
  else if (what === 'project') console.log(projectPath(version))
  else if (what === 'rundir') console.log(runDir(version))
  else {
    console.error('Usage: node versions.js tree|project|rundir <version>')
    process.exit(1)
  }
}
