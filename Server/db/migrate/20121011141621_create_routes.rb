class CreateRoutes < ActiveRecord::Migration
  def change
    create_table :routes do |t|
      t.datetime :starts
      t.datetime :ends

      t.timestamps
    end
  end
end
