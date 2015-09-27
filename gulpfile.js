var gulp  = require('gulp'),
    postcss      = require('gulp-postcss'),
    sourcemaps   = require('gulp-sourcemaps'),
    autoprefixer = require('autoprefixer'),
    minifyCss = require('gulp-minify-css'),
    concatCss = require('gulp-concat-css'),
    rename = require("gulp-rename"),
    yaml = require('js-yaml'),
    concat = require('gulp-concat'),
    fs = require('fs');


gulp.task('dev', [

]);

gulp.task('prod', function () {
    /*var version = readAssetsVersion();*/
  return gulp.src('src/main/resources/public/resources/blocks/*.css')
    /*.pipe(concat('bundle'+version+'.css'))*/
    .pipe(concatCss("bundle.css"))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(minifyCss())
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
    });


function readAssetsVersion() {
  var version = '';

  try {
    var doc = yaml.safeLoad(fs.readFileSync('target/classes/config/application-prod.yml'));
    version = doc.assets.version;
  } catch (e) {
    console.error('Fatal error. Before running scripts packaging package spring application');
    version = '';
  }

  return version;
}


//хэширование скриптов (пути чужие)
gulp.task('scripts', function() {
  var version = readAssetsVersion();
  return gulp
    .src('./src/main/resources/public/resources/scripts/*.js')
    .pipe(concat('bundle'+version+'.js'))
    .pipe(gulp.dest('./src/main/resources/public/dist/scripts/'));
});