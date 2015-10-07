var gulp  = require('gulp'),
    postcss      = require('gulp-postcss'),
    assets  = require('postcss-assets'),
    sourcemaps   = require('gulp-sourcemaps'),
    autoprefixer = require('autoprefixer'),
    minifyCss = require('gulp-minify-css'),
    concatCss = require('gulp-concat-css'),
    rename = require("gulp-rename"),
    yaml = require('js-yaml'),
    notify = require("gulp-notify"),
    clean = require('gulp-clean'),
    fs = require('fs'),
    del = require('del'),
    imagemin = require('gulp-imagemin');

gulp.task('prod', [
    'clean',
    'styles',
    'imagemin',
    'libcss:cross'
]);

gulp.task('dev', function () {
  return gulp.src('src/main/resources/public/resources/blocks/*.css')
    .pipe(concatCss("bundle.css"))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(postcss([assets({
      loadPaths: ['src/main/resources/public/resources/images/']
    })]))
    .pipe(gulp.dest('src/main/resources/public/resources/build/'))
    .pipe(notify("Update!"));
});

gulp.task('watch', function () {
    gulp.watch('src/main/resources/public/resources/blocks/*.css', ['dev'])
});

gulp.task('clean', function(cb) {
    del(['src/main/resources/public/resources/build/*'], cb)
});

gulp.task('styles', function () {
    /*var version = readAssetsVersion();*/
  return gulp.src('src/main/resources/public/resources/blocks/*.css')
    /*.pipe(concat('bundle'+version+'.css'))*/
    .pipe(concatCss("bundle.css"))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(minifyCss())
    .pipe(postcss([assets({
      loadPaths: ['src/main/resources/public/resources/images/']
    })]))
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
});

gulp.task('imagemin', function () {
    return gulp.src('src/main/resources/public/resources/images/*/*.*')
        .pipe(imagemin({
            progressive: true,
            optimizationLevel: 3,
            svgoPlugins: [{removeViewBox: false}],
        }))
        .pipe(gulp.dest('src/main/resources/public/resources/images/'));
});

gulp.task('libcss:cross', function () {
    return gulp.src('src/main/resources/public/resources/vendor/*/*.css')
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(gulp.dest('src/main/resources/public/resources/vendor/*/'));
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


//хэширование скриптов (не впилено)
gulp.task('scripts', function() {
  var version = readAssetsVersion();
  return gulp
    .src('./src/main/resources/public/resources/scripts/*.js')
    .pipe(concat('bundle'+version+'.js'))
    .pipe(gulp.dest('./src/main/resources/public/dist/scripts/'));
});