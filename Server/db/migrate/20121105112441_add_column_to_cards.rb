class AddColumnToCards < ActiveRecord::Migration
  def change
	add_column :cards, :cvv, :integer
  end
end
