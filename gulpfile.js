var gulp  = require('gulp'),
    postcss      = require('gulp-postcss'),
    sourcemaps   = require('gulp-sourcemaps'),
    autoprefixer = require('autoprefixer'),
    minifyCss = require('gulp-minify-css'),
    concatCss = require('gulp-concat-css'),
    rename = require("gulp-rename"),
    yaml = require('js-yaml'),
    notify = require("gulp-notify"),
    clean = require('gulp-clean'),
    fs = require('fs'),
    imageminOptipng = require('imagemin-optipng'),
    imageminJpegtran = require('imagemin-jpegtran'),
    inlineimage = require('gulp-inline-image');

gulp.task('prod', function () {
    /*var version = readAssetsVersion();*/
  return gulp.src('src/main/resources/public/resources/blocks/*.css')
    /*.pipe(concat('bundle'+version+'.css'))*/
    .pipe(concatCss("bundle.css"))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(minifyCss())
    .pipe(inlineimage())
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
    });

gulp.task('dev', function () {
  return gulp.src('src/main/resources/public/resources/blocks/*.css')
    .pipe(concatCss("bundle.css"))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(inlineimage())
    .pipe(gulp.dest('src/main/resources/public/resources/build/'))
    .pipe(notify("Update!"));
    });

gulp.task('watch', function () {
    gulp.watch('src/main/resources/public/resources/blocks/*.css', ['dev'])
});

gulp.task('minpng', function () {
    return gulp.src('src/main/resources/public/resources/images/*.png')
        .pipe(imageminOptipng({optimizationLevel: 3})())
        .pipe(gulp.dest('src/main/resources/public/resources/images'));
});

gulp.task('minjpg', function () {
    return gulp.src('src/main/resources/public/resources/images/*.jpg')
        .pipe(imageminJpegtran({progressive: true})())
        .pipe(gulp.dest('src/main/resources/public/resources/images'));
});

gulp.task('safari', function () {
    return gulp.src('src/main/resources/public/resources/vendor/scripts/qb/qb.css')
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(gulp.dest('src/main/resources/public/resources/vendor/scripts/qb1/'));
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