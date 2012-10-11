class CreateRouteStops < ActiveRecord::Migration
  def change
    create_table :route_stops do |t|
      t.references :route
      t.references :stop
      t.integer :stop_order
      t.integer :delay

      t.timestamps
    end
    add_index :route_stops, :route_id
    add_index :route_stops, :stop_id
  end
end
