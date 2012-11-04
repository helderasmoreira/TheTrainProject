# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20121104141347) do

  create_table "cards", :force => true do |t|
    t.string   "number"
    t.string   "card_type"
    t.date     "validity"
    t.integer  "user_id"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "cards", ["user_id"], :name => "index_cards_on_user_id"

  create_table "distances", :force => true do |t|
    t.integer  "stop1_id"
    t.integer  "stop2_id"
    t.integer  "distance"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  create_table "prices", :force => true do |t|
    t.float    "price"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  create_table "route_connections", :force => true do |t|
    t.integer  "route1_id"
    t.integer  "route2_id"
    t.integer  "stop_id"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "route_connections", ["route1_id"], :name => "index_route_connections_on_route1_id"
  add_index "route_connections", ["route2_id"], :name => "index_route_connections_on_route2_id"
  add_index "route_connections", ["stop_id"], :name => "index_route_connections_on_stop_id"

  create_table "route_stops", :force => true do |t|
    t.integer  "route_id"
    t.integer  "stop_id"
    t.integer  "stop_order"
    t.integer  "delay"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "route_stops", ["route_id"], :name => "index_route_stops_on_route_id"
  add_index "route_stops", ["stop_id"], :name => "index_route_stops_on_stop_id"

  create_table "routes", :force => true do |t|
    t.time     "starts"
    t.time     "ends"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  create_table "stops", :force => true do |t|
    t.string   "location"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  create_table "ticket_routes", :force => true do |t|
    t.integer  "ticket_id"
    t.integer  "route_id"
    t.integer  "stop_start_id"
    t.integer  "stop_end_id"
    t.date     "date"
    t.datetime "created_at",    :null => false
    t.datetime "updated_at",    :null => false
  end

  add_index "ticket_routes", ["route_id"], :name => "index_ticket_routes_on_route_id"
  add_index "ticket_routes", ["stop_end_id"], :name => "index_ticket_routes_on_stop_end_id"
  add_index "ticket_routes", ["stop_start_id"], :name => "index_ticket_routes_on_stop_start_id"
  add_index "ticket_routes", ["ticket_id"], :name => "index_ticket_routes_on_ticket_id"

  create_table "tickets", :force => true do |t|
    t.integer  "user_id"
    t.datetime "date"
    t.float    "price"
    t.boolean  "paid"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "tickets", ["user_id"], :name => "index_tickets_on_user_id"

  create_table "users", :force => true do |t|
    t.string   "name"
    t.string   "username"
    t.string   "password"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

end
