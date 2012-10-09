class DropCards < ActiveRecord::Migration
  def up
  	drop_table :cards
  end

  def down
  end
end
