// load all the things we need
// load up the user model
var User       = require('../../models/user');
// load the auth variables
var configAuth = require('./../auth'); // use this one for testing
var TvShowTimeStrategy = require('./tvshowtime')(configAuth, User);
var LocalLogin = require('./local-login')(configAuth,User);
var LocalSignup = require('./local-signup')(configAuth,User);
var FacebookStrategy = require('./facebook')(configAuth,User);
var GoogleStrategy = require('./google')(configAuth,User);
var TwitterStrategy = require('./twitter')(configAuth,User);

module.exports = function(passport) {
    // required for persistent login sessions
    // passport needs ability to serialize and unserialize users out of session
    // used to serialize the user for the session
    passport.serializeUser(function(user, done) {
        done(null, user.id);
    });

    // used to deserialize the user
    passport.deserializeUser(function(id, done) {
        User.findById(id, function(err, user) {
            done(err, user);
        });
    });

    // LOCAL LOGIN =============================================================
    passport.use('local-login',LocalLogin);
    // LOCAL SIGNUP ============================================================
    passport.use('local-signup', LocalSignup);
    // FACEBOOK ================================================================
    passport.use(FacebookStrategy);
    // TWITTER =================================================================
    passport.use(TwitterStrategy);
    // GOOGLE ==================================================================
    passport.use(GoogleStrategy);
    // tvshowtime ==================================================================
    passport.use('tvshowtime', TvShowTimeStrategy);

};
