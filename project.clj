(defproject sanakone "0.1.0-SNAPSHOT"
  :description "An app for helping you to learn Finnish"
  :url "https://github.com/oliyh/sanakone"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/clojurescript "1.9.542"]
                 [reagent "0.6.2"]
                 [re-frame "0.9.3"]]
  :target-path "target/%s"
  :source-paths ["src/cljc"]
  :test-paths ["test/cljc"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljc" "src/cljs"]
                        :figwheel {:on-jsload "sanakone.app/on-figwheel-reload"}
                        :compiler {:output-to "resources/public/cljs/main.js"
                                   :output-dir "resources/public/cljs/dev"
                                   :source-map true
                                   :main "sanakone.app"
                                   :asset-path "/cljs/dev"
                                   :optimizations :none
                                   :pretty-print true}}
                       {:id "prod"
                        :source-paths ["src/cljc" "src/cljs"]
                        :compiler {:output-to "resources/public/cljs/main.js"
                                   :output-dir "resources/public/cljs/prod"
                                   :main "sanakone.app"
                                   :asset-path "/cljs/prod"
                                   :optimizations :advanced}}]}
  :figwheel {:css-dirs ["resources/public/css"]}
  :scss {:builds {:dev {:source-dir "resources/scss"
                        :dest-dir "resources/public/css"
                        :executable "sassc"
                        :args ["-m" "-I" "scss/" "-t" "nested"]}}}

  :profiles {:dev {:source-paths ["dev" "src/cljc"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]

                                  ;; cljs
                                  [figwheel-sidecar "0.5.10"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [org.clojure/tools.reader "0.10.0"]
                                  [org.clojure/tools.trace "0.7.9"]]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[lein-cljsbuild "1.1.6"]
                             [lein-figwheel "0.5.10"]
                             [lein-scss "0.3.0"]]}})
