class AddColumnToTickets < ActiveRecord::Migration
  def change
  	add_column :tickets, :date, :string
  end
end
