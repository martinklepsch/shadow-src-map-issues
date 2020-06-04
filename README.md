# shadow-src-map-issues

```
# start serving cloud function
firebase serve --project $PROJECT
# run source maps analyzer stuff
curl -s localhost:5000/api/throw | ./sourcemap.clj | ./view-source.clj
```
