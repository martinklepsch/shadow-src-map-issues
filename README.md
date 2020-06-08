# shadow-src-map-issues

This repo tries to demonstrate an issue with broken source maps when using the `:node-library` target in Shadow-CLJS.

Two helper scripts are included to verify source mapping:

- `sourcemap.clj` looks up the locations of an error using `com.google.debugging.sourcemap.SourceMapConsumerV3` and returns the source file and line numbers as edn maps
- `view-source.clj` is a [babashka](https://github.com/borkdude/babashka) script that takes this information and prints the relevant lines for each file that is part of the project.

#### `:node-library` repro process

```
shadow-cljs release srcmaptest
```
```
node -e 'var x = require("./functions/index"); x.throw()' 2>&1 | ./sourcemap.clj | ./view-source.clj
```

#### `:node-script` repro process

**Note:** Without `--debug` this also doesn't work.
```
shadow-cljs release srcmaptestscript --debug
```
```
node script/index.js 2>&1 | ./sourcemap.clj | ./view-source.clj
```
