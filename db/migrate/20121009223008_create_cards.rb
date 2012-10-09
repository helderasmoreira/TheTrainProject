class CreateCards < ActiveRecord::Migration
  def change
    create_table :cards do |t|
      t.string :number
      t.string :type
      t.date :validity
      t.references :User

      t.timestamps
    end
    add_index :cards, :User_id
  end
end
