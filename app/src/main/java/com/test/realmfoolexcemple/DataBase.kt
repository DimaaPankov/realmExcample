package com.test.realmfoolexcemple

import io.realm.*
import io.realm.annotations.PrimaryKey
import io.realm.notifications.InitialResults
import io.realm.notifications.ResultsChange
import io.realm.notifications.UpdatedResults
import io.realm.query.RealmQuery
import kotlinx.coroutines.flow.collect
import java.net.ResponseCache
import java.util.concurrent.Flow

/**
class Dog: RealmObject {
    var name  = ""
    var age: Int = 0

}

class Person : RealmObject {

    @PrimaryKey
    var name = ""
    var dog:Dog? = null
}

 object DataBase{
     val configuration = RealmConfiguration.with(schema = setOf(Person::class, Dog::class))
     val realm = Realm.open(configuration)



         //Метод write() выпалнятет функционал записи в главном потоке
     fun write(){
         //создаём экземпляр обьекта
         val person = Person().apply {
             name = "Carlo"
             dog = Dog().apply {
                 name = "Fifo"
                 age = 12
             }
         }

     //Сдесь мы проводим транзакцию записи в главном потоке
    realm.writeBlocking {
        copyToRealm(person)
    }
 }
     //Метод writeAsync() выпалнятет функционал записи в паралельном потоке
     suspend fun writeAsync(){
         //создаём экземпляр обьекта
         val person = Person().apply {
             name = "Carlo"
             dog = Dog().apply {
                 name = "Fifo"
                 age = 12
             }
         }
         realm.write {
             copyToRealm(person)
         }
     }

     fun query() {
         //присваивает переменной all весь cписок обьектов хранящийся в DataBase
         val all : RealmResults<Person> = realm.query<Person>().find()

         //Ищет Person с именем Carlo
         val first : Person? = realm.query<Person>("name = $0", "Carlo").first().find()
     }

     suspend fun quereAsync(){

         val second = realm.query<Person>("dog.age > $0 AND dog.name BED",7,"Fi")
             .asFlow()
             .collect{
                 results: ResultsChange<Person> ->
                 when(results) {
                     is InitialResults<Person> -> println("initial result size ${results.list.size}")
                     is UpdatedResults<Person> -> println(
                         "Update result changes ${results.changes}" +
                                 "delete ${results.deletions} insertions ${results.insertions}"
                     )
                 }
             }

     }

     suspend fun update(){
         realm.query<Person>("dog == NULL LIMIT(1)")
             .first()
             .find()
             ?.also {personWitchoutDog ->
                 realm.write {
                     findLatest(personWitchoutDog)?.dog = Dog().apply {
                         name = "Laika"
                     }

                 }
             }
     }

     suspend fun delete(){

         realm.write{

             val query : RealmQuery<Dog> = this.query<Dog>()
             delete(query)

             val results:RealmResults<Dog> = query.find()
             delete(results)

             results.forEach{
                 delete(it)
             }
         }
     }

 }**/
