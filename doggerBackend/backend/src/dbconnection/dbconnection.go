package dbconnection

import (
	"gopkg.in/mgo.v2"
	"dao"
	"log"
	"time"
	"github.com/spf13/viper"
)

const (
	MONGODB_HOST string = "db:27017"
	MONGO_SCHEMA string = "db_name"
)

var (
	session  *mgo.Session
	database *mgo.Database
)

func InitMongoDB() {
	var err error
	maxWait := time.Duration(5 * time.Second)
	session, err = mgo.DialWithTimeout(MONGODB_HOST, maxWait)
	if err != nil {
		log.Println(err)
		panic(err)
	}
	session.SetMode(mgo.Monotonic, true)
	database = session.DB(viper.GetString(MONGO_SCHEMA))

	iniciarColecciones()
}

func iniciarColecciones() {
	dao.InitDeviceDAO(database)
	dao.InitLocationDAO(database)
}

func CloseMongoDB() {
	session.Close()
}
