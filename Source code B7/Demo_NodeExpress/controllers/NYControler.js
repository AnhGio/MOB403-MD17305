
const db = require('../models/nyManager.model');
const fs = require('fs');
const path = require('path');
const { randomUUID } = require('crypto');


exports.listNy = async (req, res, next) => {
    var nyList = await db.nyModel.find().populate('type')
    res.send(nyList);
};

exports.addNy = async (req, res, next) => {
    const newNy = new db.nyModel();
    const body = req.body;
    newNy.name = body.name;
    newNy.phone = body.phone;
    newNy.typeId = body.typeId;
    newNy.description = body.description;

    try {
        let new_pd = await newNy.save();
        return res.status(200).json({ cede: 0, message: 'Thêm ny thành công' });
    } catch (error) {
        return res.status(500).json({ cede: 0, message: 'Thêm ny thất bại' });
    }
};

exports.listOfType = async (req, res, next) => {
    var typeList = await db.nyTypeModel.find()
    res.send(typeList);
};


exports.upload = (req, res, next) => {
    const uerId = "123456"
    const uploadDir = path.join(__dirname, '../public/images', uerId);

    if (!fs.existsSync(uploadDir)) {
      fs.mkdirSync(uploadDir, { recursive: true });
    }
    
    const fileItem = req.file;
    console.log(fileItem);

    if (!fileItem) {
      return res.status(400).json({ message: 'No file uploaded.' });
    }

    const filePath = path.join(uploadDir, randomUUID() + ".jpg");

    fs.rename(fileItem.path, filePath, (err) => {
        if (err) {
          return res.status(500).json({ message: 'Error uploading file.' });
        } else {
        return res.json({ message: 'File uploaded successfully.' });
        }
    });
};

exports.images = (req, res) => {
    const uerId = "123456"
    const directoryPath = path.join(__dirname, '../public/images', uerId);
  
    fs.readdir(directoryPath, (err, files) => {
      if (err) {
        console.error('Error reading directory:', err);
        return res.status(500).json({ error: 'Error reading directory.' });
      }
  
      const imageUrls = files.map((file) => {
        return `${req.protocol}://${req.get('host')}/images/${uerId}/${file}`;
      });
  
      res.json({ images: imageUrls });
    });
  };