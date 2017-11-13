package effectasside.random

class RandomEffect {

}
object RandomInterpreter {
  def apply(seed: Long): RandomInterpreter = new RandomInterpreter(new util.Random(seed))
  def apply(random: util.Random): RandomInterpreter = new RandomInterpreter(random)
}

class RandomInterpreter(val random: util.Random) {
  def nextDouble() = random.nextDouble()
  def nextInt(n: Int) = random.nextInt(n)
  def nextBoolean() = random.nextBoolean()
  def shuffle[A](s: Vector[A]) = random.shuffle(s)
  def use[T](f: util.Random => T) = f(random)
}