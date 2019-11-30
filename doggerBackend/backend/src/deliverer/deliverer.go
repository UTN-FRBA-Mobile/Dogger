package deliverer

import (
	"dto"
	"fmt"
	"log"
	"golang.org/x/net/context"
	firebase "firebase.google.com/go"
	"firebase.google.com/go/messaging"
	"time"
	"github.com/spf13/viper"
)

var (
	ctx 			context.Context
	client 			*messaging.Client
	messages 		[]*messaging.Message
	dispatch_time	time.Duration
)

func InitProcess(){
	fmt.Println("Deliverer InitProcess")
	var err error
	dispatch_time, err = time.ParseDuration(viper.GetString("dispatch_time"))
	if err != nil {
		log.Println("The format of dispatch time is incorrect")
		panic(err)
		return
	}
	err = ConnectService()
	if err != nil {
		fmt.Println("there was a problem connecting to the firebase service")
		return
	} else {
		fmt.Println("Connection with Firebase was succesfully")
		go Sender()
	}
}

func Sender(){
	for {
		time.Sleep(dispatch_time)
		for _, message := range messages {
			response, err := client.Send(ctx, message)
			fmt.Println("Sended", response)
			if err != nil {
				log.Fatalln(err)
			}
		}
  	}
}

func ConnectService() (err error){
	fmt.Println("connecting with firebase")
	app, err := firebase.NewApp(context.Background(), nil)
	if err != nil {
		log.Fatalf("error initializing app: %v\n", err)
	}
	ctx = context.Background()
	client, err = app.Messaging(ctx)
	if err != nil {
		log.Fatalf("error getting Messaging client: %v\n", err)
	}
	fmt.Println("connect to firebase")
	return
}

func RegisterDevice(device dto.DeviceDTO) (err error){
	message := &messaging.Message{
		Token: device.Id_device,
		//Si la push notification es silenciosa sacar el atributo Notification
		//Notification: &messaging.Notification{
		//	Title: "Hola!",
		//	Body:  "Como estas " + device.Id_user + "?",
		//},
		Data: map[string]string{"dummy":"fake",},
		//Se debe sacar Notification y agregar Data para que pueda procesarse en Background y Foreground
	}
	response, err := client.Send(ctx, message)
	fmt.Println("Send Response", response)
	if err != nil {
		log.Fatalln(err)
	}
	messages = append(messages, message)
	fmt.Println("messages", messages)
	return
}

func UnregisterDevice(device dto.DeviceDTO) {
	var indexToDelete int
	for i := 0; i < len(messages); i++ {
		if messages[i].Token == device.Id_device{
			indexToDelete = i
			messages = append(messages[:indexToDelete], messages[indexToDelete+1:]...)
			break
		}
	}
	fmt.Println("messages", messages)
}
