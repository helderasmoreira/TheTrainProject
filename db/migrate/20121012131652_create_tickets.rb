class CreateTickets < ActiveRecord::Migration
  def change
    create_table :tickets do |t|
      t.references :user
      t.references :route
      t.references :stop_start
      t.references :stop_end
      t.datetime :date
      t.float :price
      t.boolean :paid

      t.timestamps
    end
    add_index :tickets, :user_id
    add_index :tickets, :route_id
    add_index :tickets, :stop_start_id
    add_index :tickets, :stop_end_id
  end
end
