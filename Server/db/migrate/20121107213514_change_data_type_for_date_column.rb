class ChangeDataTypeForDateColumn < ActiveRecord::Migration
  def up
  	change_table :tickets do |t|
      t.change :date, :string
    end
  end

  def down
  end
end
