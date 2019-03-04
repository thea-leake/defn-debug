(ns defn-debug.core
  (:require [clojure.tools.logging :as log]))

(defn print-if-debug [message]
  "Prints debug message if defn-debug-enabled is defined and truthy.
  does nothing if defn-debug-enabled is not defined."
  (let [is_enabled? (resolve 'defn-debug-enabled)]
    (if is_enabled?
      (if (var-get is_enabled?)
        (println message)))))

(defn print-invoke [name args]
  "Prints out map with fn name, args, and env at time of defn.
  Should be called at time of fn invocation."
  (print-if-debug {:fn-name name
                   :fn-args args
                   :exec-point "invocation"}))

(defn print-return [name result]
  "Prints out map with fn name, and return value.
  Should be called when fn returns."
  (print-if-debug {:fn-name name
                   :fn-return result
                   :exec-point "return"}))

(defn single-arity? [form]
  (instance? clojure.lang.PersistentVector form))

(defn multi-arity? [form]
  (instance? clojure.lang.PersistentList form))

(defmacro build-arity [fn-name fn-args forms]
  `(list
    ~fn-args
    (do
      (print-invoke ~fn-name ~fn-args)
      (let [result# (do ~@forms)]
        (do
          (print-return ~fn-name result#)
          result#)))))

(defmacro build-arities [fn-name fn-form]
  `(cond
    (single-arity? ~fn-form) (build-arity
                             ~fn-name
                             (first ~fn-form)
                             (rest ~fn-form))
    (multi-arity? ~fn-form) (cons (build-arities ~fn-name ~fn-form)
                                  (build-arities ~fn-name ~fn-form))
    :else  '()))

(defmacro defnp [fn-name & fn-forms]
  "Creates a fn with debug logging of fn args, and fn return val"
  `(let [fn-arities# (build-arities `~~fn-name ~fn-forms)]
     (concat (defn ~fn-name) fn-arities#)))
