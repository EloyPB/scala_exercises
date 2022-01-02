def fun(f: (Boolean, Boolean) => Boolean)(a: Boolean, b: Boolean): Boolean =
  f(a, b)

def and = fun((a, b) => a & b)
and(true, false)

def or = fun(_ | _)
or(true, false)