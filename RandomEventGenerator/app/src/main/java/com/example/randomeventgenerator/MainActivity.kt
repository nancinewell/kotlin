package com.example.randomeventgenerator

//Initialize RandomEvents()
var randomEvents = RandomEvents()

fun main(args: Array<String>) {
    //Demonstrate adding items to the randomEvents (and seeding the events list)
    println("Add a few events to the list.")

    randomEvents.addEvent("A Trick", "Peeves played a trick on the rangers. The rangers are glued to their seats for the class! The magic will lift at the end of class.")
    randomEvents.addEvent("Teacher Appreciation", "Stop to take stock of your good fortune! Say or do something nice for your teachers today!")
    randomEvents.addEvent("Pool of Life","You take a swim in the pool of life and heal up to 2 HP.")
    randomEvents.addEvent("Touched by an Angel","An angel visits the healers and bestows her powers upon them. All healers gain 1 SP & 1 HP")
    randomEvents.addEvent("The sky is the limit!","...Unless you're playing indoors. Everyone has 30 seconds to make a paper airplane. The furthest to fly wins 100XP!")

    //Demonstrate displaying events
    println(" \nDisplay all events")
    randomEvents.displayEvents()

    //Demonstrate modifying an event
    println(" \nModify an event")
    randomEvents.modifyEvent("The sky is the limit!", "...Unless you're playing indoors. Everyone has 1 minute to make a paper airplane. The furthest to fly wins 1000XP!")

    //Demonstrate rolling a random event
    println("\nRoll a random event for the day")
    randomEvents.rollEvent()

    //Demonstrate deleting an event
    println("\nDelete event with id 5")
    randomEvents.deleteEvent(5)

    //Show all events to show the event was deleted
    println(" \nDisplay all events")
    randomEvents.displayEvents()

    //Engage user
    println("\nNow that you've seen what this app can do, you try!")
    userInterface()
}


//data class to hold events- primitive types only
data class Event(val id: Int, val title: String, var event: String)

//class to manage behaviors/actions of the events
class RandomEvents(){
    //A collection to store the events
    var events = mutableSetOf<Event>()

    //Count of the collection for various uses
    var count = events.count()

    //And incremented variable to ensure each new item has a different id.
    var id = 0

    //Add an event to the collection
    fun addEvent(_title: String, _event: String){
        val newEvent = Event(id,_title, _event)
        events.add(newEvent)
        count = events.count()
        id++
        println("New event added: $newEvent")
    }

    //Alter an event in the collection, located by title...
    // You can't actually modify an item in a set (much to my chagrin), so we'll remove it and add the new one with the changes.
    fun modifyEvent(_title: String, _event: String ){
        var eventId = -1

        events.forEach {
            //if the submitted title is in the collection, locate it.
            if(it.title == _title){
                eventId = it.id
            }
        }
        //if the title was located, delete the old event and add a new with the submitted info.
        if (eventId > -1) {
            deleteEvent(eventId)
            addEvent(_title, _event)
            println("Event modified. New event details: " + events.elementAt(count - 1))
        } else {
            //if it was not located, alert the user and reengage the user interface.
            println("Invalid entry.")
            userInterface()
        }
    }

    //Delete an event from the collection, located by id
    fun deleteEvent(_id: Int){
        //locate the event by id number
        val targetEvent = events.first{it.id == _id}

        //if the event is in the collection, remove it, update the count, and notify the user.
        if(events.contains(targetEvent)){
            events.remove(targetEvent)
            count = events.count()
            println("Event id $_id deleted")
        } else {
            //if the event was not located, alert the user and reengage the user interface.
            println("Invalid entry.")
            userInterface()
        }

    }

    //Display all events
    fun displayEvents(){
        //Display the total number of events to the user.
        println("There are $count events.")
        //If there are events in the collection, display them.
        if (count > 0){
            println("All events:")
            for (i in events) println("$i")
        }
    }

    //Retrieve a random event
    fun rollEvent(){
        //If there are events in the collection...
        if(count > 0){
            //get a random number within the range of the collection count.
            val randomNumber = (0..count-1).shuffled().first()
            println("\nToday's Random Event:")
            //Iterate through the events and display the one of the selected number.
            var counter = 0
            for (i in events){
                if (counter != randomNumber){
                    counter++
                } else {
                    println(i.title)
                    println(i.event)
                    break
                }
            }
        }

    }
}
//Function to engage the user to interact with the events.
fun userInterface(){
    println("\nWhat would you like to do?")
    println("Enter the number for your selection. \n 1: Add Event\n 2: Modify Event\n 3: Delete Event\n 4: Display Events \n 5: Roll Event \n 6: Quit")
    //gather intention from user
    val userInput = readln()

    //use user input to proceed
    when(userInput){
        "1" -> {
            //Adding an event
            println("Great! Let's add an event.")
            //Gather more details to add the event
            promptUserForDetails("Add")
        }

        "2" -> {
            //Modify an event
            println("Awesome! Let's modify your event.")
            //Gather more details to modify the event
            promptUserForDetails("Modify")
        }

        "3" -> {
            //Delete an event.
            println("Alrighty! Let's delete an event. First, which number is it?")
            //Display events so user can get the id of the event they want to delete
            randomEvents.displayEvents()
            //User enter the id of the event they want to delete
            val id = readln()
            //Delete the event
            randomEvents.deleteEvent(id.toInt())
            //Redirect back into the user interface
            userInterface()
        }

        "4" -> {
            //Display the events
            println("You got it! Here are all of the events.")
            randomEvents.displayEvents()
            //Redirect back into the user interface
            userInterface()
        }

        "5" -> {
            //Roll a random event
            randomEvents.rollEvent()
            //Redirect back into the user interface
            userInterface()
        }
        //Exit
        "6" -> println("Have a great day!")
        else -> {
            //Alert the user of an invalid entry
            println("Invalid entry. Please try again. ")
            //Redirect back into the user interface
            userInterface()
        }
    }
}

//Function to gather additional data to proceed with user selection
fun promptUserForDetails(typeOfDetails: String){

    //obtain title of event to either create or search for.
    println("What is the title of your event?")
    val titleInput = readln()

    when(typeOfDetails){
        //When adding the event, we need the event details, then add a new random event
        "Add" -> {
            //Gather the details of the new event
            println("And the details of the event?")
            val eventInput = readln()
            //add the event
            randomEvents.addEvent(titleInput, eventInput)
            //Redirect back into the user interface
            userInterface()
        }
        "Modify" -> {
            //Gather the new details of the event
            println("What are the new details of the event?")
            val eventInput = readln()
            //Modify the event
            randomEvents.modifyEvent(titleInput, eventInput)
            //Redirect back into the user interface
            userInterface()
        }
    }
}
