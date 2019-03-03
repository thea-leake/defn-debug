(ns defn-debug.core
  (:require [clojure.tools.logging :as log]))

(defn print-invoke [name args env]
  (println {:fn-name name
   :fn-args args
   :fn-env env
   :exec-point "invocation"}))

(defmacro defnp [fn-name fn-args  & forms]
  `(defn ~fn-name ~fn-args
     (do
       (print-invoke ~fn-name ~fn-args ~&env)
       (let [result# (do ~@forms)]
         (do
           (println {:fn-name ~fn-name
                     :fn-return result#
                     :exec-point "return"})
           result#)))))
