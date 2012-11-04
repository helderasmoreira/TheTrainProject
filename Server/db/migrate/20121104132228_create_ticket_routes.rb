class CreateTicketRoutes < ActiveRecord::Migration
  def change
    create_table :ticket_routes do |t|
      t.references :ticket
      t.references :route
      t.references :stop_start
      t.references :stop_end
      t.date :date

      t.timestamps
    end
    add_index :ticket_routes, :ticket_id
    add_index :ticket_routes, :route_id
    add_index :ticket_routes, :stop_start_id
    add_index :ticket_routes, :stop_end_id
  end
end
