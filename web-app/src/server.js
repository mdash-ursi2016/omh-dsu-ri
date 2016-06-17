'use strict';

// Constants
const express = require('express');
const bodyParser = require('body-parser');
const fs = require('graceful-fs');
const https = require('https');
const PORT = 8000;

// Accessing key and certificate
const privateKey = fs.readFileSync('sslcert/server.key', 'utf8');
const certificate = fs.readFileSync('sslcert/server.crt', 'utf8');
const credentials = {key: privateKey, cert: certificate};

// App configuration
const app = express();
const httpsServer = https.createServer(credentials, app);
app.use(express.static('public'));
app.use( bodyParser.json() ); // for parsing application/json bodies
app.use( bodyParser.urlencoded({ extended: true}) ); // for parsing URL-encoded bodies

// Functions
app.get('/', function (req, res) {
    res.send('Hello, World!\n');
});

app.get('/index.htm', function (req, res) {
    res.sendFile('/usr/src/app/public/html/index.htm' );
});

// Later will use the jsonParser to get the accessToken from the req
// Or use it to parse the json request and process the POST to the server?
app.post('/process_post', function (req, res) {
    // Prepare output in JSON format
    let response = {
	"Username" : req.body.username,
	"Password" : req.body.password
    };
    res.json(response);
});


httpsServer.listen(PORT);
console.log('Running on https://143.229.6.40:' + PORT);
