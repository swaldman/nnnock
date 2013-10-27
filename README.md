#### nnnock #####

An naive, unoptimized, very literal implementation of the [Urbit project](http://www.urbit.org)'s Nock VM in Scala. 

Because, why not?

For the core implementation, see [here](https://github.com/swaldman/nnnook/blob/master/src/main/scala/com/mchange/sc/v1/nnnock/package.scala).

For a quick program testing the examples given with the [spec](http://www.urbit.org/2013/08/22/Chapter-2-nock.html), see [here](https://github.com/swaldman/nnnook/blob/master/src/main/scala/com/mchange/sc/v1/nnnock/Check.scala).

To run the test program, just download sbt and execute `sbt run` from the base directory of this repository.

Lamely, there is no parser, but you can evaluate expressions as a kind of scala-internal DSL. Expressions like `[a b c d]` should be written as `Cell(a,b,c,d)`. Evaluate using the function `*( noun )`. For example:

```
swaldman@whisper:~/development/gitproj/nnnock$ sbt console

Welcome to Scala version 2.10.2 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_17).
Type in expressions to have them evaluated.
Type :help for more information.

scala> import com.mchange.sc.v1.nnnock._;
import com.mchange.sc.v1.nnnock._

scala> *( Cell(42,Cell(8,Cell(1,0),8,Cell(1,6,Cell(5,Cell(0,7),4,0,6),Cell(0,6),9,2,Cell(0,2),Cell(4,0,6),0,7),9,2,0,1)) )
res0: com.mchange.sc.v1.nnnock.Noun = Atom(41)

```


