
# compile and run
nim compile --run greetings.nim
# compile and run with arguments
nim compile --run greetings.nim arg1 arg2
# short forms
nim c -r greetings.nim
# compile a release version
nim c -d:release greetings.nim
# to disable checks and compare with C
nim c -d:danger greetings.nim

-- String/Character literals
# raw string - backslash \ is not an escape character
r"C:\program files\nim"
# long string literals - can span over multiple lines and \ is not an escape
# character either
""" ... """

-- Comments
# A comment.

var myVariable: int ## a documentation comment

##multilines comments
#[
You can have any Nim code text commented
out inside this with no indentation restrictions.
      yes("May I ask a pointless question?")
  #[
     Note: these can be nested!!
  ]#
]#

-- Numbers
1_000_000 (one million). 
A number that contains a dot (or 'e' or 'E') is a floating-point literal: 1.0e9 (one billion). Hexadecimal literals are prefixed with 0x, binary literals with 0b and octal literals with 0o

-- Variables
var x, y: int # declares x and y to have the type `int`
# can use identation to list whole section of vars
var
  x, y: int
  # a comment can occur here too
  a, b, c: string

-- Constants & Let
const
  x = 1
  # a comment can occur here too
  y = 2
  z = y + 5 # computations are possible

let x = "abc" # introduces a new variable `x` and binds a value to it
x = "xyz"     # Illegal: assignment to `x`

The difference between let and const is: let introduces a variable that can not be re-assigned, const means "enforce compile time evaluation and put it into a data section"

const input = readLine(stdin) # Error: constant expression expected (not a constant)
let input = readLine(stdin)   # works

-- Assignments
var x, y = 3  # assigns 3 to the variables `x` and `y` (can be overloaded)
echo "x ", x  # outputs "x 3"
echo "y ", y  # outputs "y 3"
x = 42        # changes `x` to 42 without changing `y`
echo "x ", x  # outputs "x 42"
echo "y ", y  # outputs "y 3"

-- Controls
let name = readLine(stdin)
if name == "":
  echo "Poor soul, you lost your name?"
elif name == "name":
  echo "Very funny, your name is name."
else:
  echo "Hi, ", name, "!"

--
let name = readLine(stdin)
case name
of "":
  echo "Poor soul, you lost your name?"
of "name":
  echo "Very funny, your name is name."
of "Dave", "Frank":   #<-- , for multiple matches?
  echo "Cool name!"
else:
  echo "Hi, ", name, "!"

--
from std/strutils import parseInt

echo "A number please: "
let n = parseInt(readLine(stdin))
case n
of 0..2, 4..7: echo "The number is in the set: {0, 1, 2, 4, 5, 6, 7}"
of 3, 8: echo "The number is 3 or 8"
else: discard  # <-- since `n` is an Int, need this catch-all statement
# discard is a `do nothing` statement
# Nim does not allow silently throwing away a return value

--
echo "What's your name? "
var name = readLine(stdin)
while name == "":
  echo "Please tell me your name: "
  name = readLine(stdin) # no `var`, because we do not declare a new variable here

--
for i in countup(1, 10):
  echo i
# --> Outputs 1 2 3 4 5 6 7 8 9 10 on different lines

for i in countdown(10, 1):
  echo i
# --> Outputs 10 9 8 7 6 5 4 3 2 1 on different lines

.. iterator

for i in 1 .. 10:
  ...

for i in 0 ..< 10:    # < exclude upper bound
  ...  # the same as 0 .. 9

var s = "some string"
for idx, c in s[0 .. ^1]:  
  ... # ^1 is the last element, ^2 would be one before it, and so on

^ Builtin roof operator that can be used for convenient array access. a[^x] is a shortcut for a[a.len-x].

Useful iterators:
items and mitems, which provides immutable and mutable elements respectively, and
pairs and mpairs which provides the element and an index number (immutable and mutable respectively)

for index, item in ["a","b"].pairs:
  echo item, " at index ", index
# => a at index 0
# => b at index 1
