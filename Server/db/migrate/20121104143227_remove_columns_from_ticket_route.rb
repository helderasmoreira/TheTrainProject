class RemoveColumnsFromTicketRoute < ActiveRecord::Migration
  def up
  	remove_column :ticket_routes, :stop_start_id
  	remove_column :ticket_routes, :stop_end_id
  	add_column :ticket_routes, :stop_start_order, :integer
  	add_column :ticket_routes, :stop_end_order, :integer
  end

  def down
  end
end
