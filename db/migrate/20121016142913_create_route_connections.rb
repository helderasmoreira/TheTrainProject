class CreateRouteConnections < ActiveRecord::Migration
  def change
    create_table :route_connections do |t|
      t.references :route1
      t.references :route2
      t.references :stop

      t.timestamps
    end
    add_index :route_connections, :route1_id
    add_index :route_connections, :route2_id
    add_index :route_connections, :stop_id
  end
end
