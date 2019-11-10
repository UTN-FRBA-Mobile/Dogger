package config

import(
	"os"
	"log"
	"github.com/spf13/viper"
)

func InitConfig(){
	viper.AddConfigPath("./config/")
	viper.SetConfigType("yaml") 
	if os.Getenv("ENVIRONMENT") == "PROD" {
		viper.SetConfigName("config.prod")
   	} else {
		viper.SetConfigName("config.dev")
    }
	err := viper.ReadInConfig()

	if err != nil {
		log.Fatalln("Fatal error config file", err)
	}
}