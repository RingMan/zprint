(ns user
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.java.javadoc :refer [javadoc] :as jdoc]
            [clojure.java.shell :as sh]
            [clojure.pprint :refer (pprint pp cl-format)]
            [clojure.repl :refer :all]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer (refresh refresh-all)]
            [clojure.walk :as walk :refer [prewalk]]
            [expound.alpha :refer [expound]]
            [integrant.repl :refer [clear go halt prep init reset reset-all]]
            [zprint.config :refer [default-zprint-options get-options get-default-options
                                   reset-default-options!  reset-options!]]
            [zprint.core :as zp
             :refer [zprint zprint-str zprint-file zprint-file-str
                     zprint-fn zprint-fn-str
                     czprint czprint-str czprint-fn czprint-fn-str
                     configure-all! set-options!]]
            [zprint.ansi :refer [ansi-codes color-str]]))

(alias 'c 'clojure.core)

(integrant.repl/set-prep! (constantly {}))

