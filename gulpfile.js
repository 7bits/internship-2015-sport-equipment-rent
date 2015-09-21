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


/*gulp.task('prod',[
    'css:concat',
    'css:autoprefixer',
    'css:minify',
    'hash'
]);*/

gulp.task('dev', [

]);

gulp.task('prod', function () {
    var version = readAssetsVersion();
  return gulp.src('src/main/resources/public/resources/*/*.css')
    .pipe(concat('bundle'+version+'.css'))
    .pipe(postcss([ autoprefixer({ browsers: ['last 4 versions'] }) ]))
    .pipe(minifyCss())
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
    });


gulp.task('css:concat', function () {
  return gulp.src('src/main/resources/public/resources/*/*.css')
    .pipe(concatCss("result.css"))
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
});

gulp.task('css:autoprefixer', function () {
    return gulp.src('src/main/resources/public/resources/build/result.min.css')
        .pipe(sourcemaps.init())
        .pipe(postcss([ autoprefixer({ browsers: ['last 2 versions'] }) ]))
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest('src/main/resources/public/resources/build/'));
});

gulp.task('css:minify', function() {
  return gulp.src('src/main/resources/public/resources/build/result.min.css')
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


gulp.task('hash', function() {
  var version = readAssetsVersion();
  return gulp
    .src('src/main/resources/public/resources/build/result.min.css')
    .pipe(concat('bundle'+version+'.css'))
    .pipe(gulp.dest('src/main/resources/public/resources/build/'));
});


//хэширование скриптов (пути чужие)
gulp.task('scripts', function() {
  var version = readAssetsVersion();
  return gulp
    .src('./src/main/resources/public/resources/scripts/*.js')
    .pipe(concat('bundle'+version+'.js'))
    .pipe(gulp.dest('./src/main/resources/public/dist/scripts/'));
});

//gulp.task('default', ['scripts', 'css:hash']);