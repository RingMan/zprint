(ns user
  (:require [clojure.core :as c]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.java.javadoc :refer [javadoc] :as jdoc]
            [clojure.java.shell :as sh]
            [clojure.pprint :refer [pprint pp cl-format]]
            [clojure.repl :refer :all]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl
             :refer [clear refresh refresh-all set-refresh-dirs]]
            [clojure.walk :as walk :refer [prewalk]]
            [expound.alpha :refer [expound]]
            [zprint.config :refer [default-zprint-options get-options get-default-options
                                   reset-default-options!  reset-options!]]
            [zprint.core :as zp
             :refer [zprint zprint-str zprint-file zprint-file-str
                     zprint-fn zprint-fn-str
                     czprint czprint-str czprint-fn czprint-fn-str
                     configure-all! set-options!]]
            [zprint.ansi :refer [ansi-codes color-str]]))

(set-refresh-dirs "src" "dev")

(defn init []
  :initialized)

(defn reset []
  (refresh :after 'user/init))

