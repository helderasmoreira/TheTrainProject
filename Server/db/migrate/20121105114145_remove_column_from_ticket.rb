class RemoveColumnFromTicket < ActiveRecord::Migration
  def up
	remove_column :tickets, :date
  end

  def down
  end
end
