var express = require('express');
var router = express.Router();

const multer  = require('multer')
const upload = multer({ dest: 'uploads/' })

var nyController = require('../controllers/NYControler');

router.get('/list-ny', nyController.listNy);
router.post('/add-ny', nyController.addNy);

router.get('/list-nyType', nyController.listOfType);

router.post('/upload',upload.single('file'), nyController.upload);
router.get("/images", nyController.images);

module.exports = router;
