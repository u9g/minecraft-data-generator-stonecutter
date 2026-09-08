# minecraft-data-generator

This tool generates minecraft-data files by running a server on the client classpath using a Fabric mod.
The supported versions are enumerated in versions.json.

The sources are shared across versions with [Stonecutter](https://stonecutter.kikugie.dev/).
Version-specific code is selected with comment conditions:

```java
//? if >=1.13 {
return Language.getInstance().get(key);
//?} else {
/*return Registries.LANGUAGE.translate(key);*/
//?}
```

There are two Stonecutter trees, one per mapping family, because Yarn and Mojang names differ for nearly every symbol:

| Tree      | Versions          | Mappings                                          |
|-----------|-------------------|---------------------------------------------------|
| `yarn/`   | 1.7 – 1.21.3      | Legacy Yarn up to 1.13, Yarn from 1.14            |
| `mojmap/` | 1.21.5 and later  | Mojang mappings; unobfuscated official names from 26.1 |

Each tree has:

* `src/` – the shared sources, checked in with the tree's `vcsVersion` active
* `build.gradle` – the build script applied to every version of the tree
* `stonecutter.gradle` – the Stonecutter controller, holding the active version
* `versions/<version>/gradle.properties` – per-version settings: `mc.version`, `mc.mappings`, `mc.mappings.build`, `mixins`, and optionally `java.version` / `lombok.version`

## Usage

Replace `<tree>` and `<version>` in the command below, for example `:yarn:1.8.9:runServer` or `:mojmap:26.1:runServer`.
`node tools/versions.js project <version>` prints the project path for a version.

For Linux/Mac OS:

```bash
./gradlew :<tree>:<version>:runServer
```

For Windows:

```bash
gradlew.bat :<tree>:<version>:runServer
```

You can then find the minecraft-data in the `<tree>/versions/<version>/run/server/minecraft-data` directory.

## Editing

`<tree>/src` always reflects one version, the active one. To work on another version, switch it:

```bash
./gradlew ":yarn:Set active project to 1.16"
```

Stonecutter then rewrites the comment conditions in `yarn/src` so the 1.16 branches are live and the others are commented out.
Do not commit a switched tree; switch back to the `vcsVersion` from settings.gradle before committing.

Compile a single version with `./gradlew :yarn:1.16:build`, or every version of a tree with `./gradlew :yarn:build`.

## Adding a new version

Generally, our automated PR system will automatically create a new PR for a new version,
so you just have to clone the auto opened PR and continue work on it.

### Manual setup

To add a new version manually, run

```
npm run bump <version>
```

For example, `npm run bump 26.2` will:
* Create `mojmap/versions/26.2/gradle.properties` as a copy of the latest version's
* Register `26.2` in settings.gradle and make it the active and checked-in version
* Add the version to `versions.json`

### Updating

Then fix the code issues caused by the new version. Wrap new code in a condition so older versions keep the previous code:

```java
//? if >=26.2 {
newApi();
//?} else {
/*oldApi();*/
//?}
```

Refer to Minecraft source code diffs for reference, as the code uses standard Mojmaps.

You can use an IDE like IntelliJ IDEA to manually fix the issues or an LLM agent that has access to the source code diff.

Once everything compiles, you can commit the changes, push them to your fork and create a pull request.

Once your PR was accepted and merged, the new version will be available in the next release.

## Technical info

By configuring Unimined (the mod build tool used by this project) to run the server, the server will be started with the client classpath.
The Minecraft client always contains a copy of the whole server code.
This is so because the integrated server is always bundled with clients and Mojang has always additionally bundled the dedicated server code within the client code.
