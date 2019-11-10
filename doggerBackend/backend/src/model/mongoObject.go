package model

type MongoObject struct {
	Id interface{} `bson:"_id" binding:"required"`
}
