class AddColumnsToTicketTable < ActiveRecord::Migration
  def change
  	add_column :tickets, :departureTime, :string
  	add_column :tickets, :arrivalTime, :string
  	add_column :tickets, :duration, :string
  	add_column :tickets, :departure, :string
  	add_column :tickets, :arrival, :string
  end
end
