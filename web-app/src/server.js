'use strict';

// Constants
const express = require('express');
const bodyParser = require('body-parser');
//const fs = require('graceful-fs');
//const https = require('https');
const PORT = 8000;

/*
// Accessing key and certificate
const privateKey = fs.readFileSync('sslcert/server.key', 'utf8');
const certificate = fs.readFileSync('sslcert/server.crt', 'utf8');
const credentials = {key: privateKey, cert: certificate};
*/

// App configuration
const app = express();
//const httpsServer = https.createServer(credentials, app);
app.use(express.static('public'));
app.use( bodyParser.json() ); // for parsing application/json bodies
app.use( bodyParser.urlencoded({ extended: true}) ); // for parsing URL-encoded bodies

// Functions
app.get('/', function (req, res) { // Homepage will be log-in page (index.htm)
    res.sendFile('/usr/src/app/public/html/index.htm' );
});

app.get('/test.htm', function (req, res) { // Page to test getting the token
    res.sendFile( '/usr/src/app/public/html/test.htm' );
});

// Will redirect to this URL, which will have the token
app.get('/login*', function (req, res) {
    let response = {};
    res.json(response);
});


app.listen(PORT);
console.log('Running on http://143.229.6.40:' + PORT);
