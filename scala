

Some acronyms:
scalac      the Scala compiler
scala       the Scala REPL and script runner
scala-cli   Scala CLI, interactive toolkit for Scala
sbt, sbtn   The sbt build tool  (Scala build tool)
amm         Ammonite is an enhanced REPL
scalafmt    Scalafmt is the Scala code formatter

To generate from Giter8 templates using SBT, for e.g. a basic hello world
sbt new scala/scala3.g8
<enter 'hello-world' in prompt>
cd hello-world
sbt  (opens up console)
~run  (~ for rerun on every file save)

To just compile and run a file:
scalac hello.scala
scala hello

To bring up REPL:
scala

--
Variables:
// immutable
val a = 0

// mutable
var b = 1

val msg = "Hello, world"
msg = "Aloha"   // "reassignment to val" error; this won’t compile
var msg = "Hello, world"
msg = "Aloha"   // this compiles because a var can be reassigned

Type inference:
val x: Int = 1   // explicit
val x = 1        // implicit; the compiler infers the type

--
Data types:
In Scala, everything is an object

val b: Byte = 1
val i: Int = 1
val l: Long = 1
val s: Short = 1
val d: Double = 2.0
val f: Float = 3.0

val i = 123   // defaults to Int
val j = 1.0   // defaults to Double

val x = 1_000L   // val x: Long = 1000
val y = 2.2D     // val y: Double = 2.2
val z = 3.3F     // val z: Float = 3.3

// for large numbers
var a = BigInt(1_234_567_890_987_654_321L)
var b = BigDecimal(123_456.789)

Where Double and Float are approximate decimal numbers, BigDecimal is used for precise arithmetic.

val name = "Bill"   // String
val c = 'a'         // Char

String interpolation
val firstName = "John"
val mi = 'C'
val lastName = "Doe"

// Notice `s` in front is a string interpolator
// `f` is also another interpolator
println(s"Name: $firstName $mi $lastName")   // "Name: John C Doe"
println(s"2 + 2 = ${2 + 2}")   // prints "2 + 2 = 4"

val x = -1
println(s"x.abs = ${x.abs}")   // prints "x.abs = 1"

// `f` before a string allows printf-style formatting.
// this example prints:
// "Joe is 42 years old, and weighs"
// "180.5 pounds."
println(f"$name is $age years old, and weighs $weight%.1f pounds.")

--
val name = "Martin Odersky"

val quote = s"""
|$name says
|Scala is a fusion of
|OOP and FP.
""".stripMargin.replaceAll("\n", " ").trim

// result:
// "Martin Odersky says Scala is a fusion of OOP and FP." 

--
“operators” like + and - are really methods in Scala, not operators
var a = 2
a *= 2      // 4
a /= 2      // 2

---
// different ways to get the current date and time
import java.time.*

val a = LocalDate.now
    // 2020-11-29
val b = LocalTime.now
    // 18:46:38.563737
val c = LocalDateTime.now
    // 2020-11-29T18:46:38.563750
val d = Instant.now
    // 2020-11-30T01:46:38.563759Z
val d = LocalDate.of(2020, 1, 21)
val d = LocalDate.of(2020, Month.JANUARY, 21)
val d = LocalDate.of(2020, 1, 1).plusDays(20) 

// measuring time elapsed
def time[A](f: => A) = {
  val s = System.nanoTime
  val ret = f
  println("time: "+(System.nanoTime-s)/1e6+"ms")
  ret
}

scala> time { 10*2 }
time: 0.054212ms
res1: Int = 20
--
Functions
// technically this is a method, not a function
def add(a: Int, b: Int) = a + b
add(2, 2)   // 4

// multilines
def addAndDouble(a: Int, b: Int): Int =
  // imagine this requires
  // multiple lines
  (a + b) * 2

--
Anonymous Function
// a function (an anonymous function assigned to a variable)
val log = (s: String) => console.log(s)

// a scala method. methods tend to be used much more often,
// probably because they’re easier to read.
def log(a: Any) = console.log(a)

// a function or a method can be passed into another
// function or method
def printA(a: Any, f: log: Any => Unit) = log(a) 


// Unit is a value type which carries no meaningful information. There is exactly one instance of Unit which we can refer to as: ()
// In statement based languages, `void` is used for methods that don't return anything; in Scala, Unit is used for same purpose
// becoz in Scala, everything is an expression, means they return a result

----
Classes
Scala has both classes and case classes.
class -> more for OOP style applications
https://docs.scala-lang.org/scala3/book/domain-modeling-oop.html
case classes -> for FP style applications
https://docs.scala-lang.org/scala3/book/domain-modeling-fp.html

--
if, then, else, while, do, for

if x == 1 then println(x)
if x == 1 then
  println("x is 1, as you can see:")
  println(x)
if x < 0 then
  println("negative")
else if x == 0
  println("zero")
else
  println("positive")

def min(a: Int, b: Int): Int =
  if a < b then a else b

while i < 3 do
  println(i)
  i += 1

--
// Scala
val nums = List(1, 2, 3)

// preferred
for i <- ints do println(i)

// also available
for (i <- ints) println(i)

// preferred
for i <- ints do
  val i = i * 2
  println(j)

// also available
for (i <- nums) {
  val j = i * 2
  println(j)
}

// this works
scala> val x::xs = List(1,2,3)
val x: Int = 1
val xs: List[Int] = List(2, 3)

--
Generators in loops
for
  i <- 1 to 2
  j <- 'a' to 'b'  // range generator for alphabets? 
  k <- 1 to 10 by 5  // 1 to 10 by 5, means 1 and 6 only; these are equivalent to 3 embedded for loops
do
  println(s"i: $i, j: $j, k: $k")

// with guards `if`
for
  i <- 1 to 10   // see this, range generator ;)
  if i % 2 == 0
  if i < 5
do
  println(i)


--
`yield` keyword
val doubledNums = for (n <- nums) yield n * 2

val ucNames = for (name <- names) yield name.capitalize

val names = List("_adam", "_david", "_frank")
val capNames = for (name <- names) yield {
    val nameWithoutUnderscore = name.drop(1)
    val capName = nameWithoutUnderscore.capitalize
    capName
}
// capNames: List[String] = List(Adam, David, Frank)
// shorter versions
val capNames = for (name <- names) yield name.drop(1).capitalize
// can also use curly braces
val capNames = for (name <- names) yield { name.drop(1).capitalize }

// notice the end result above is a List, becoz the `names` input is a List
// if its a generator, it should be an IndexedSeq
scala> val l1 =
     |  for
     |      i <- 1 to 3
     |  yield
     |      i * 10
     |
val l1: IndexedSeq[Int] = Vector(10, 20, 30)

--
Match expressions
val day = 1

// later in the code ...
val monthAsString = day match
  case 1 => "January"
  case 2 => "February"
  case _ => "Other"

val numAsString = i match
  case 1 | 3 | 5 | 7 | 9 => "odd"
  case 2 | 4 | 6 | 8 | 10 => "even"
  case _ => "too big"

// Matchable is a trait
def isTruthy(a: Matchable) = a match
  case 0 | "" => false
  case _ => true

def isPerson(x: Matchable): Boolean = x match
  case p: Person => true
  case _ => false

---
Collections

Common immutable sequences are:
    List
    Vector

Common mutable sequences are:
    Array
    ArrayBuffer

Scala also has mutable and immutable Maps and Sets.
val strings = List("a", "b", "c")
val strings = Vector("a", "b", "c")
val strings = ArrayBuffer("a", "b", "c")

val set = Set("a", "b", "a") // result: Set("a", "b")
val map = Map(
  "a" -> 1,
  "b" -> 2,
  "c" -> 3
)

List
// to, until
(1 to 5).toList                   // List(1, 2, 3, 4, 5)
(1 until 5).toList                // List(1, 2, 3, 4)

(1 to 10 by 2).toList             // List(1, 3, 5, 7, 9)
(1 until 10 by 2).toList          // List(1, 3, 5, 7, 9)
(1 to 10).by(2).toList            // List(1, 3, 5, 7, 9)

('d' to 'h').toList               // List(d, e, f, g, h)
('d' until 'h').toList            // List(d, e, f, g)
('a' to 'f').by(2).toList         // List(a, c, e)

// range method
// companion object? to create list
List.range(1, 3)                  // List(1, 2)
List.range(1, 6, 2)               // List(1, 3, 5)

List.fill(3)("foo")               // List(foo, foo, foo)
List.tabulate(3)(n => n * n)      // List(0, 1, 4)
List.tabulate(4)(n => n * n)      // List(0, 1, 4, 9)

--
Functional methods
// these examples use a List, but they’re the same with Vector
val a = List(10, 20, 30, 40, 10)      // List(10, 20, 30, 40, 10)
a.contains(20)                        // true
a.distinct                            // List(10, 20, 30, 40)
a.drop(2)                             // List(30, 40, 10)
a.dropRight(2)                        // List(10, 20, 30)
a.dropWhile(_ < 25)                   // List(30, 40, 10)
a.filter(_ < 25)                      // List(10, 20, 10)
a.filter(_ > 100)                     // List()
a.find(_ > 20)                        // Some(30)
a.head                                // 10
a.headOption                          // Some(10)
a.init                                // List(10, 20, 30, 40)
a.last                                // 10
a.lastOption                          // Some(10)
a.slice(2,4)                          // List(30, 40)
a.tail                                // List(20, 30, 40, 10)
a.take(3)                             // List(10, 20, 30)
a.takeRight(2)                        // List(40, 10)
a.takeWhile(_ < 30)                   // List(10, 20)

// differences in concat, prependedAll, prepended, appended, appendedAll
scala> List(1) ++ List(2,3,4)
val res10: List[Int] = List(1, 2, 3, 4)

scala> List(1) ++: List(2,3,4)
val res11: List[Int] = List(1, 2, 3, 4)

scala> List(1) +: List(2,3,4)
val res12: List[Matchable] = List(List(1), 2, 3, 4)

// kinda same as below
scala> List(1,2)::List(3,4)
val res19: List[Matchable] = List(List(1, 2), 3, 4)


scala> List(1) :+ List(2,3,4)
val res13: List[Matchable] = List(1, List(2, 3, 4))

scala> List(1) :++ List(2,3,4)
val res14: List[Int] = List(1, 2, 3, 4)

// map, flatMap
val fruits = List("apple", "pear")
fruits.map(_.toUpperCase)             // List(APPLE, PEAR)
fruits.flatMap(_.toUpperCase)         // List(A, P, P, L, E, P, E, A, R)

val nums = List(10, 5, 8, 1, 7)
nums.sorted                           // List(1, 5, 7, 8, 10)
nums.sortWith(_ < _)                  // List(1, 5, 7, 8, 10)
nums.sortWith(_ > _)                  // List(10, 8, 7, 5, 1)

List(1,2,3).updated(0,10)             // List(10, 2, 3)
List(2,4).union(List(1,3))            // List(2, 4, 1, 3)

// zip
val women = List("Wilma", "Betty")    // List(Wilma, Betty)
val men = List("Fred", "Barney")      // List(Fred, Barney)
val couples = women.zip(men)          // List((Wilma,Fred), (Betty,Barney))

// initialize a sequence of elements
val seq_elements : Seq[Double] = Seq(3.5, 5.0, 1.5)
println(s"Elements = $seq_elements")

// find the maximum element using reduce function
val maximum : Double = seq_elements.reduce(_ max _)
println(s"Maximum element = $maximum")

// find the minimum element using reduce function
val minimum : Double = seq_elements.reduce(_ min _)
println(s"Minimum element = $minimum")

--
by default, all from Left to Right
reduce -> just like fold, but without the initial value
fold -> with initial value
List.fold... looking at the signature, expect BOTH acc and incremental value to be the same type 
fold[A1 >: A](z: A1)(op: (A1, A1) => A1): A1

List.foldLeft.. the acc type can be different
foldLeft[B](z: B)(op: (B, A) => B): B


scan -> just scan from L to R

--
how to check (and convert) type?
https://www.scala-lang.org/api/3.1.2/scala/Any.html#isInstanceOf-ecf
getClass
isInstanceOf[A]
// or more cheeky one (doesnt guarantee to always work)
variable match
	case _: Type => true
	case _ => false

// type casting
asInstanceOf[A]



--
Tuples
// can hold different data types
val a = ("eleven")
val b = ("eleven", 11)
val c = ("eleven", 11, 11.0)
val d = ("eleven", 11, 11.0, Person("Eleven"))

d(0)   // "eleven"
d(1)   // 11

scala> (1,2)._2   // access by ordinal
val res41: Int = 2


--
Enums
enum Color:
  case Red, Green, Blue

scala> enum Color(val rgb: Int):
     |   case Red   extends Color(0xFF0000)
     |   case Green extends Color(0x00FF00)
     |   case Blue  extends Color(0x0000FF)
     |
// defined class Color

scala> Color.Green.rgb // rgb can be access becoz of `val` (public?)
val res2: Int = 65280


// can parameterize an enum
enum Planet(mass: Double, radius: Double):
  case Mercury extends Planet(3.303e+23, 2.4397e6)  // so 
  case Venus   extends Planet(4.869e+24,6.0518e6)
  case Earth   extends Planet(5.976e+24,6.37814e6)
  // more planets here ...

  private final val G = 6.67300E-11
  def surfaceGravity = G * mass / (radius * radius)
  def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

// usage
scala> import Planet.*

scala> Mercury
val res16: Planet = Mercury

scala> Mercury.surfaceGravity
val res17: Double = 3.7030267229659395

scala> Mercury.surfaceWeight(21)
val res18: Double = 77.76356118228473

scala> Mercury.mass // cannot be access due to no `val`
-- Error: ----------------------------------------------------------------------
1 |Mercury.mass
  |^^^^^^^^^^^^
  |value mass cannot be accessed as a member of (Planet.Mercury : Planet) from module class rs$line$3$.
1 error found


----
Things i find weird :

scala> seq_elements
val res10: Seq[Double] = List(3.5, 5.0, 1.5)

scala> seq_elements.reduce(_ max _) # this works, but dunno how to make sense of the syntax
val res11: Double = 5.0

scala> seq_elements.reduce(max) # removing the _ doesnt work anymore
-- [E006] Not Found Error: -----------------------------------------------------
1 |seq_elements.reduce(max)
  |                    ^^^
  |                    Not found: max
  |
  | longer explanation available when compiling with `-explain`
1 error found

scala> import scala.math.*  # need to import it from Math package

scala> seq_elements.reduce(max) # then it works
val res13: Double = 5.0

#the only possible explaination is 
#under compile time ops
#seems tagged under `experimental`
https://www.scala-lang.org/api/3.1.2/scala/compiletime/ops/double$.html

type Max[X <: Double, Y <: Double] <: Double
Maximum of two Double singleton types.
# more questions:
	1. what is a `type` ?
	2. why the strange <: ops?
	3. what is `object` , diff between an `object` and a `class`?

# compared to scala.math function
def max(x: Double, y: Double): Double

# difference between length and size
length is defined in GenSeqLike, so length only exists for Seqs, 
whereas size exists for all Traversables. 
For Seqs, however, size simply delegates to length

--
Types:
Any 
 |
 -> Matchable (Means can be pattern matched)
        |
        |
      -----------------------------------------
     |                                         | 
     v                                         v
  AnyRef
(correspond to java.lang.object)      (scala native types)
Every user-defined                  Double, Float, Long, Int, Short, (BigInt and BigDecimal - for percision) Byte, Char, Unit, and Boolean
type is a subtype of AnyRef

TypeCasting of a AnyVal subtype:
val x: Long = 987654321
val y: Float = x.toFloat  // 9.8765434E8 (note that `.toFloat` is required because the cast results in precision loss)
val z: Long = y  // Error

Nothing - subtype of all types, bottom type
A common use is to signal non-termination, such as a thrown exception, program exit, or an infinite loop—i.e., it is the type of an expression which does not evaluate to a value, or a method that does not return normally
Null - subtype of AnyRef. Used to interoperate with other JVM languages. General usage is considered bad.

Generics Class (or traits):
// here we declare the type parameter A
//          v
class Stack[A]:
  private var elements: List[A] = Nil
  //                         ^
  //  Here we refer to the type parameter
  //          v
  def push(x: A): Unit = { elements = elements.prepended(x) }
  def peek: A = elements.head
  def pop(): A =
    val currentTop = peek
    elements = elements.tail
    currentTop

val stack = Stack[Int]
stack.push(1)
stack.push(2)
println(stack.pop())  // prints 2
println(stack.pop())  // prints 1

## Intersection Types (A & B -  both of the type A and of the type B at the same time):
trait Resettable:
  def reset(): Unit

trait Growable[A]:
  def add(a: A): Unit

def f(x: Resettable & Growable[String]): Unit =
  x.reset()
  x.add("first")

// x is required to be both a Resettable and a Growable[String].

// Members of an intersection type A & B are all the members of A and all the members of B. Therefore, as shown, Resettable & Growable[String] has member methods reset and add.

## Union Types (A | B - either of the type A or of the type B):
case class Username(name: String)
case class Password(hash: Hash)

def help(id: Username | Password) =
  val user = id match
    case Username(name) => lookupName(name)
    case Password(hash) => lookupPassword(hash)

Inference of Union Types:
scala> val a = if (true) name else password
val a: Object = Username(Eve) // the inferred type is Object

scala> val b: Password | Username = if (true) name else password
val b: Password | Username = Username(Eve) // if want the least supertype Password | Username, need to specify it explicityly

## Algebraic Datatypes (ADTs):
enum Option[+T]: // `covariant` type parameter T (hence the +T)
	case Some(x: T)
	case None

//Option enum with a covariant type parameter T consisting of two cases
// `extends` clauses given explicitly
enum Option[+T]:
  case Some(x: T) extends Option[T]
  case None       extends Option[Nothing]

scala> Option.Some("hello")
val res1: t2.Option[String] = Some(hello)

scala> Option.None
val res2: t2.Option[Nothing] = None

// ADTs can define additional methods
enum Option[+T]:
  case Some(x: T)
  case None

  def isDefined: Boolean = this match
    case None => false
    case Some(_) => true

object Option: // companion Object - seems like mean Static Class methods
  def apply[T >: Null](x: T): Option[T] =
    if (x == null) None else Some(x)

// usage
scala> Option.Some("hello").isDefined
val res3: Boolean = true

scala> Option.None.isDefined
val res4: Boolean = false

scala> Option.apply("hello")
val res5: Option[String] = Some(hello)

scala> Option.apply(Nothing)
-- [E006] Not Found Error: -----------------------------------------------------
1 |Option.apply(Nothing)
  |             ^^^^^^^
  |             Not found: Nothing
  |
  | longer explanation available when compiling with `-explain`
1 error found

scala> Option.apply(null)
val res6: Option[Null] = None

-
Recursive Enumerations:
enum Nat:
  case Zero
  case Succ(n: Nat)

// For example the value Succ(Succ(Zero)) represents the number 2 in an unary encoding.

enum List[+A]:
  case Nil
  case Cons(head: A, tail: List[A])

scala> List.Cons(2,List.Cons(1,List.Nil))
val res8: List[Int] = Cons(2,Cons(1,Nil))


## Generalized ADTs (GADTs):
Since we can always be more `explicit`, it is also possible to express types that are much more powerful: generalized algebraic datatypes (GADTs).

scala> enum Box[T](contents: T): // able to explicitly control based on distinct type T
     |   case IntBox(n: Int) extends Box[Int](n)
     |   case BoolBox(b: Boolean) extends Box[Boolean](b)
     | object Box:
     |   def extract[T](b: Box[T]): T = b match
     |     case IntBox(n)  => n + 1
     |     case BoolBox(b) => !b
     |
// defined class Box
// defined object Box

scala> Box.extract(Box.IntBox(1))
val res0: Int = 2

scala> Box.extract(Box.BoolBox(1))
-- [E007] Type Mismatch Error: -------------------------------------------------
1 |Box.extract(Box.BoolBox(1))
  |                        ^
  |                        Found:    (1 : Int)
  |                        Required: Boolean
  |
  | longer explanation available when compiling with `-explain`
1 error found

scala> Box.extract(Box.BoolBox(false))
val res1: Boolean = true

---
// Assume these 3 type definitions:
trait Item { def productNumber: String }
trait Buyable extends Item { def price: Int }
trait Book extends Buyable { def isbn: String }

// 3 parameterized types:
// an example of an invariant type - consume T, produce T
trait Pipeline[T]: 
  def process(t: T): T

// an example of a covariant type - produce a value of Type T
trait Producer[+T]:
  def make: T

// an example of a contravariant type - consumes values of Type T
trait Consumer[-T]:
  def take(t: T): Unit

// Normal subtyping relationship
// Book <: Buyable <: Item

## Invariant Type
def oneOf(
  p1: Pipeline[Buyable], // can only take a Buyable
  p2: Pipeline[Buyable],
  b: Buyable
): Buyable =
  val b1 = p1.process(b) // since b1, b2 here only produce Buyable
  val b2 = p2.process(b)
  if b1.price < b2.price then b1 else b2
// Array or Set are invariant

## Covariant Type
// Subtyping relationship for Producer
// Producer[Buyable] <: Producer[Item]
def makeTwo(p: Producer[Buyable]): Int =
  p.make.price + p.make.price

val bookProducer: Producer[Book] = ???
makeTwo(bookProducer) // make is happy to accept a Book, which is a subtype of Buyable (at least a Buyable)
// List, Seq, Vector are covariants

## Contravariant Types
// the type parameter is only used in argument position
// Subtyping relationship - the other way round for Consumer
// Consumer[Item] <: Consumer[Buyable]
def takeThree(c: Consumer[Item], item: Item): Unit =
	c.take(item)

val itemConsumer: Consumer[Item] = ???
val item: Item = ???
val buyable: Buyable = ??? // happy to accept a buyable
takeThree(itemConsumer, buyable)

--
Example of subtyping relationships induced by variance annotations
trait Function[-A, +B]: // consumes values of type A, produces values of type B
  def apply(a: A): B

val f: Function[Buyable, Buyable] = b => b

// producer
// OK to return a Buyable where a Item is expected
val g: Function[Buyable, Item] = f

// consumer
// OK to provide a Book where a Buyable is expected
val h: Function[Book, Buyable] = f

--
Domain modeling

Classes - a template for the creation of object instances
class Person(var name: String, var vocation: String)
class Book(var title: String, var author: String, var year: Int)
class Movie(var name: String, var director: String, var year: Int)

no `new` keyword is needed when instantiating object instances
becoz of Universal Apply Methods
https://docs.scala-lang.org/scala3/reference/other-new-features/creator-applications.html

a companion object with 2 apply methods is generated with each class
object StringBuilder:
  inline def apply(s: String): StringBuilder = new StringBuilder(s)
  inline def apply(): StringBuilder = new StringBuilder()

val p = Person("Robert Allen Zimmerman", "Harmonica Player")
p.name       // "Robert Allen Zimmerman"
p.vocation   // "Harmonica Player"

// since the parameters were created as `var` fields
// they can be mutated
p.name = "Bob Dylan"
p.vocation = "Musician"

Classes can extend traits and abstract classes

// Default and named parameter values
class Socket(val timeout: Int = 5_000, val linger: Int = 5_000):
  override def toString = s"timeout: $timeout, linger: $linger"

val s = Socket()                  // timeout: 5000, linger: 5000
val s = Socket(2_500)             // timeout: 2500, linger: 5000
val s = Socket(10_000, 10_000)    // timeout: 10000, linger: 10000
val s = Socket(timeout = 10_000)  // timeout: 10000, linger: 5000
val s = Socket(linger = 10_000)   // timeout: 5000, linger: 10000

--
Auxiliary constructors

import java.time.*

// [1] the primary constructor
class Student(
  var name: String,
  var govtId: String
):
  private var _applicationDate: Option[LocalDate] = None
  private var _studentId: Int = 0

  // [2] a constructor for when the student has completed
  // their application
  def this(
    name: String,
    govtId: String,
    applicationDate: LocalDate
  ) =
    this(name, govtId)
    _applicationDate = Some(applicationDate)

  // [3] a constructor for when the student is approved
  // and now has a student id
  def this(
    name: String,
    govtId: String,
    studentId: Int
  ) =
    this(name, govtId)
    _studentId = studentId

// usage
val s1 = Student("Mary", "123")
val s2 = Student("Mary", "123", LocalDate.now)
val s3 = Student("Mary", "123", 456)

## Objects
An object is a class that has exactly one instance.
// similar with `static` class members

## Companion Objects
Just an object which has the same name as a class (and declared in the same file). Similarly, the corresponding class is called the object’s companion class. A companion class or object can access the private members of its companion.

traits (as interfaces, and for abstract classes as well - from scala 3 onwards)
	- with abstract and concrete members

// a member = can be a field OR a method (takes in arguments)
// only those part of a "class" (domain constructs) can declare but NOT define a member
// for e.g. def someVal: Int

// traits can have parameters
trait Pet(name: String):
  def greeting: String
  def age: Int
  override def toString = s"My name is $name, I say $greeting, and I’m $age"

class Dog(name: String, var age: Int) extends Pet(name):
  val greeting = "Woof"

val d = Dog("Fido", 1)

The rule of thumb is to use classes whenever you want to create instances of a particular type, and traits when you want to decompose and reuse behaviour.

## Case Class - are used to model immutable data structures
// difference between oridinary class and case class
// case class also have a few other helpful methods

scala> class BT1(name: String, l: Int)
// defined class BT1

scala> BT1("CC", 1)
val res0: BT1 = BT1@5600a5da

scala> res0.copy()
-- [E008] Not Found Error: -----------------------------------------------------
1 |res0.copy()
  |^^^^^^^^^
  |value copy is not a member of BT1 - did you mean res0.clone?
1 error found

scala> case class BT2(name: String, l: Int)
// defined case class BT2

scala> BT2("CC", 2)
val res1: BT2 = BT2(CC,2)

scala> res1.copy()
val res2: BT2 = BT2(CC,2)

scala> res1.copy(l=3)
val res3: BT2 = BT2(CC,3)

// since fields of case class are assumed to be immutable
- An unapply method is generated, which allows you to perform pattern matching on a case class (that is, case Person(n, r) => ...).
- A copy method is generated in the class, which is very useful to create modified copies of an instance.
- equals and hashCode methods using structural equality are generated, allowing you to use instances of case classes in Maps.
- A default toString method is generated, which is helpful for debugging.

--
// Case classes can be used as patterns
christina match
  case Person(n, r) => println("name is " + n)

// `equals` and `hashCode` methods generated for you
val hannah = Person("Hannah", "niece")
christina == hannah       // false

// `toString` method
println(christina)        // Person(Christina,niece)

// built-in `copy` method
case class BaseballTeam(name: String, lastWorldSeriesWin: Int)
val cubs1908 = BaseballTeam("Chicago Cubs", 1908)
val cubs2016 = cubs1908.copy(lastWorldSeriesWin = 2016)
// result:
// cubs2016: BaseballTeam = BaseballTeam(Chicago Cubs,2016)

## Case objects:
Case objects are to objects what case classes are to classes
Case objects are useful when you need to pass immutable messages around

--
Extension methods
- let you add methods to closed classes

for e.g.
// imagine the below Circle class is created
case class Circle(x: Double, y: Double, radius: Double)

// instead of having to use a (static) separate object
object CircleHelpers:
  def circumference(c: Circle): Double = c.radius * math.Pi * 2

val aCircle = Circle(2, 3, 5)

// without extension methods
CircleHelpers.circumference(aCircle)

--
//  we can just use extension to Circle
extension (c: Circle)
  def circumference: Double = c.radius * math.Pi * 2

val aCircle = Circle(2, 3, 5)

aCircle.circumference

// multiple extension methods can be defined
extension (c: Circle)
  def circumference: Double = c.radius * math.Pi * 2
  def diameter: Double = c.radius * 2
  def area: Double = math.Pi * c.radius * c.radius

--
mixins




---
Domain Modeling

OOP - 
    a. traits  (like interfaces/abstract class, can define both abstract and concrete methods, cannot be instantiated)
    b. classes (can extend traits, can be instantiated)

FP -
    a. Enums - to model ADTs
    b. case classes - adding `case` keyword in front gives several effects and benefits
    c. traits

---
Function - Multiple parameters group

//for e.g.
def add(a: Int, b: Int, c: Int) = a + b + c
//just split the params out like this
def sum(a: Int)(b: Int)(c: Int) = a + b + c
//can use like this
scala> sum(1)(2)(3)
res0: Int = 6

//each parameter group can have multiple input parameters
def doFoo(firstName: String, lastName: String)(age: Int) = ???


-
lets define our own "while" loop control structure call "whilst"

var i = 0
whilst (i < 5) {
    println(i)
    i += 1
}

Both parameter groups use by-name parameters
 -- is it just a function name WITHOUT specifying the input params?
 -- see testCondition: => Boolean.. there's no testCondition: A => B like that

def whilst(testCondition: => Boolean)(codeBlock: => Unit) = ???

def whilst(testCondition: => Boolean)(codeBlock: => Unit) {
    while (testCondition) {
        codeBlock
    }
}

-- pattern of breaking into parameter groups
1. Use one or more parameter groups to break the input parameters into different “compartments”
2. Specifically define the parameter in the last parameter group as a by-name parameter so the function can accept a custom block of code

----
Partially applied functions vs currying (What's the diff?)

Currying means is that a function that takes multiple arguments can be translated into a series of function calls that each take a SINGLE argument

Partially applied means several arguments can be applied(?) at any one position

scala> def sum(a: Int)(b: Int)(c: Int) = a + b + c
def sum(a: Int)(b: Int)(c: Int): Int

scala> sum(1)
val res0: Int => Int => Int = Lambda$1332/1028574311@3f357c9d

scala> sum(1)(_)  // good
val res1: Int => Int => Int = Lambda$1343/1573356572@4d67d5a4

scala> sum(1)(_)(_)   // nope
-- [E081] Type Error: ----------------------------------------------------------
1 |sum(1)(_)(_)
  |       ^
  |     Missing parameter type

scala> sum(1)(2)(_)  // this ok
val res3: Int => Int = Lambda$1374/920033195@28831d69

scala> sum(1)(2)_ // ok
val res4: Int => Int = Lambda$1375/577269211@2e29f28e

scala> sum(1)(_: Int)(3)  // this is what separates PAF from pure currying (only one by one from Left to Right)
val res5: Int => Int = Lambda$1386/1775113446@699d96bc

--
scala> def add(x: Int, y: Int) = x + y
add: (x: Int, y: Int)Int

// Function2 - function with 2 parameters
scala> (add _).isInstanceOf[Function2[_, _, _]]
res0: Boolean = true

// https://www.scala-lang.org/api/3.1.2/scala/Function2.html
scala> val addCurried = (add _).curried // use this to convert a Tupled function into a curried one
addCurried: Int => (Int => Int) = <function1>

scala> addCurried(1)(2)
res1: Int = 3

scala> val addCurriedTwo = addCurried(2)
addCurriedTwo: Int => Int = <function1>

scala> addCurriedTwo(10)
res2: Int = 12

--
scalac -Xprint:all Currying.scala   //config option to show all stages of compilation for this program
scalac -Xshow-phases // to list out all phases

--
Difference between methods and functions

// methods are invoked on an instance of a class
def incrementMethod(x: Int): Int = x + 1
// A method is a member of the enclosing class or object and can be invoked like this
val three = someInstance.incrementMethod(2)

Functions(aka lambdas)
// can be invoked independently of class or object
val incrementFunction = (x: Int) => x + 1
val three = incrementFunction(2)

// function values are instances of the FunctionN family of traits
// what the compiler does
val incrementFunction = new Function1[Int, Int] {
    override def apply(x: Int): Int = x + 1
  }
val three = incrementFunction.apply(2) // desugared from incrementFunction(2)

summary: a method of a class ; vs a field of type FunctionN 

--
eta-Expansion : converting a method to a function

val incrementF = incrementMethod _     //<-- add the underscore at the end to turn the method into a Int => Int function, like below:
val incrementF = (x: Int) => incrementMethod(x)

the other way is to give it the type in advance:
val incrementF2: Int => Int = incrementMethod

def multiArgAdder(x: Int)(y: Int) = x + y
val add2 = multiArgAdder(2) _ // this is of type Int => Int, a PAF

val three = add2(1)

--
scala> def multiArgAdder(x: Int)(y: Int) = x + y
def multiArgAdder(x: Int)(y: Int): Int
// compiler auto eta-expand
scala> List(1,2,3).map(multiArgAdder(3))
val res30: List[Int] = List(4, 5, 6)

scala> def add(x: Int, y: Int) = x + y
     | val addF = add _
def add(x: Int, y: Int): Int
val addF: (Int, Int) => Int = Lambda$1831/1511125481@3f72126c // eta-expansion with 2 arguments type (Int, Int) => Int

// eta-expansion turns a method into a function which will take the remaining argument lists (however large)

----
to have MULTIPLE arguments (varargs) of the SAME type, use the * modifier
// seems like only can use DURING `def`
scala> def printme(s: String*) = s.foreach(println)
def printme(s: String*): Unit

scala> printme("abc", "def")
abc
def

to UNPACK a List as a list of arguments, use the _* modifier
scala> printme(List("hey", "there"):_*)
hey
there

---
Sorting
// There's an Ordering class for all the basic data types
// https://www.scala-lang.org/api/3.1.2/scala/math/Ordering$.html
scala> "ABACCDE"
val res0: String = ABACCDE

scala> val sb = StringBuilder(res0)
val sb: StringBuilder = ABACCDE

// to "sort" a string
scala> sb.sorted(Ordering.Char)
val res3: StringBuilder = AABCCDE

scala> sb.sorted(Ordering.Char.reverse)
val res2: StringBuilder = EDCCBAA

// actually can do directly
// becoz extension methods from
// https://www.scala-lang.org/api/3.1.2/scala/collection/StringOps.html 
// are implicitly added
scala> "ABCDEFABD".sorted(Ordering.Char)
val res13: String = AABBCDDEF


// to group a string by char count
// def groupMapReduce[K, B](key: Char => K)(f: Char => B)(reduce: (B, B) => B): Map[K, B]
// arguments explained: (function for Key)(function for initial value)(function
// to derive next value)
scala> sb.groupMapReduce(identity)(_ => 1)(_ + _)
val res4: Map[Char, Int] = HashMap(E -> 1, A -> 2, B -> 1, C -> 2, D -> 1)


---
Higher Order Function (HOF) parameter
vs
by-name parameter




