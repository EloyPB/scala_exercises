def fun(f (Boolean, Boolean) => Boolean)(a: Boolean, b: Boolean): Boolean =
  f(a, b)

and = f((a, b) => a & b)
and(true, false)