class RemoveColumnsFromTicket < ActiveRecord::Migration
  def up
  	remove_column :tickets, :route_id
  	remove_column :tickets, :stop_start_id
  	remove_column :tickets, :stop_end_id
  end

  def down
  end
end
