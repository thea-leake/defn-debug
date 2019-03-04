(ns defn-debug.core
  (:require [clojure.tools.logging :as log]))

(defn print-if-debug [message]
  "Prints debug message if defn-debug-enabled is defined and truthy.
  does nothing if defn-debug-enabled is not defined."
  (let [is_enabled? (resolve 'defn-debug-enabled)]
    (if is_enabled?
      (if (var-get is_enabled?)
        (println message)))))

(defn print-invoke [name args env]
  "Prints out map with fn name, args, and env at time of defn.
  Should be called at time of fn invocation."
  (print-if-debug {:fn-name name
                   :fn-args args
                   :defn-env env
                   :exec-point "invocation"}))

(defn print-return [name result]
  "Prints out map with fn name, and return value.
  Should be called when fn returns."
  (print-if-debug {:fn-name name
                   :fn-return result
                   :exec-point "return"}))

(defmacro defnp [fn-name fn-args  & forms]
  "Creates a fn with debug logging of fn args, and fn return val"
  `(defn ~fn-name ~fn-args
     (do
       (print-invoke `~~fn-name ~fn-args ~&env)
       (let [result# (do ~@forms)]
         (do
           (print-return `~~fn-name result#)
           result#)))))
