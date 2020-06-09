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
            [zprint.cutil :refer :all]
            [zprint.rutil :refer :all]
            [zprint.ansi :refer [ansi-codes color-str]]))

(set-refresh-dirs "src" "dev")

(def zp-colors
  {:brace :white,
   :bracket :white,
   :char :bright-cyan,
   :comma :bright-white,
   :comment :bright-black,
   :deref :red,
   :false :bright-magenta,
   :fn :bright-red,
   :hash-brace :white ,
   :hash-paren :white,
   :keyword :bright-blue,
   :nil :bright-magenta,
   :none :white,
   :number :bright-magenta,
   :paren :white,
   :quote :bright-yellow,
   :regex :bright-cyan,
   :string :bright-green,
   :symbol :bright-white,
   :syntax-quote :bright-yellow,
   :syntax-quote-paren :white,
   :true :bright-magenta,
   :uneval :bright-red,
   :unquote :bright-yellow,
   :unquote-splicing :bright-yellow,
   :user-fn :bright-yellow})

(def zp-opt
  {:color-map zp-colors
   :map {:comma? true
         :indent 2
         :key-order [:tag :id :name]
         :key-color (kcol (ks->v [:tag :id :name] [:bold]) zp-colors)}
   :vector {:wrap-after-multi? false}
   :uneval {:color-map zp-colors}})

(defn init []
  (update-print-fn! render)
  (set-options! zp-opt)
  :initialized)

(defn reset []
  (refresh :after 'user/init))

