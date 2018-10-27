class CreateQuestions < ActiveRecord::Migration[5.1]
  def change
    create_table :questions do |t|
      t.string :site_name
      t.text :security_question
      t.text :security_answer

      t.timestamps
    end
  end
end
