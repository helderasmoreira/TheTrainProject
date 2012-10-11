class FixColumnType < ActiveRecord::Migration
  def up
  	change_table :routes do |t|
      t.change :starts, :time
      t.change :ends, :time
    end
  end

  def down
  end
end
