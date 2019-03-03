(ns defn-debug.core
  (:require [clojure.tools.logging :as log]))

(defn print-invoke [name args env]
  (println {:fn-name name
   :fn-args args
   :fn-env env
   :exec-point "invocation"}))

(defn print-return [name result]
  (println {:fn-name name
            :fn-return result
            :exec-point "return"}))


(defmacro defnp [fn-name fn-args  & forms]
  `(defn ~fn-name ~fn-args
     (do
       (print-invoke `~~fn-name ~fn-args ~&env)
       (let [result# (do ~@forms)]
         (do
           (print-return `~~fn-name result#)
           result#)))))
