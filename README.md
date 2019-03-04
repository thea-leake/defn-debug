# defn-debug

A Clojure library that gives an alternative defn that provides toggle-able printing of args and results of functions declared with it.
In most cases repl driven development would be preferable to find this information, and this should never be used outside of debugging.
There are some situations where something like this is super handy though, so I made a small library for it.

## Usage
defnp currently does not work on [multi arity functions](http://clojure-doc.org/articles/language/functions.html#multi-arity-functions), though it does work with [variadic functions](http://clojure-doc.org/articles/language/functions.html#variadic-functions).

* Define functions with `defnp`, example:
```
(defnp grr [a b ] (a b b))
```

* Ensure that `defn-debug-enabled` is `def`'d and truthy, example:
```
(def defn-debug-enabled true)
```
If `defn-debug-enabled` is undefined functions defined with `defnp`  will simply not log debug output. 


Here is an example of usage.
``` lisp
defn-debug.core> (defnp grr [a b ] (a b b))
#'defn-debug.core/grr
defn-debug.core> (grr + 1)
2
defn-debug.core> (def defn-debug-enabled nil)
#'defn-debug.core/defn-debug-enabled
defn-debug.core> (grr + 1)
2
defn-debug.core> (def defn-debug-enabled true)
#'defn-debug.core/defn-debug-enabled
defn-debug.core> (grr + 1)
{:fn-name #function[defn-debug.core/grr], :fn-args [#function[clojure.core/+] 1], :defn-env nil, :exec-point invocation}
{:fn-name #function[defn-debug.core/grr], :fn-return 2, :exec-point return}
2
```

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
