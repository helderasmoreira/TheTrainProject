class CreateDistances < ActiveRecord::Migration
  def change
    create_table :distances do |t|
      t.integer :stop1_id
      t.integer :stop2_id
      t.integer :distance

      t.timestamps
    end
  end
end
