package flappy


class ScalaSort {
  def sort(xs: Array[SaveInfo]) {
    def swap(i: Int, j: Int) {
      val t = xs(i);
      xs(i) = xs(j);
      xs(j) = t
    }
    def sort1(l: Int, r: Int) {
      val pivot = xs((l + r) / 2).getScore
      var i = l;
      var j = r
      while (i <= j) {
        while (xs(i).getScore > pivot) i += 1
        while (xs(j).getScore < pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }
    sort1(0, xs.length - 1)
  }
}
