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

; next two fns lifted from whidbey and adapted to czprint

(defn render
  "Renders the given value for display by pretty-printing it on the given writer
  using czprint."
  [value writer]
  (binding [*out* writer] (czprint value)))

(defn update-print-fn!
  "Updates nREPL's printing configuration to use `render-fn` nREPL 0.6.0+ only."
  [render-fn]
  (some-> (find-ns 'nrepl.middleware.print)
          (ns-resolve '*print-fn*)
          (var-set render-fn)))

(update-print-fn! render)

