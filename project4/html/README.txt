ssh -i face_east1a.pem ubuntu@54.165.1.85
./mongodb/bin/mongod --dbpath=./mongodb/data
./mongodb/bin/mongo

scp -i face_east1a.pem -r face_east1a.pem ubuntu@54.165.1.85:/var/www/html ./Information-System/

