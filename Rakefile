require 'rake/clean'
# rake/clean définit CLEAN (Rake::FileList) pour les fichiers temporaires
# et CLOBBER (Rake::FileList) pour les fichiers de sortie

chapters = %w[intro chapter1 conc] # %w définit un tableau de chaînes

REVEALJS_VERION = "3.5.0"

# Array : https://ruby-doc.org/core-trunk/Array.html
# Rake::FileList : http://ruby-doc.org/stdlib-trunk/libdoc/rake/rdoc/Rake/FileList.html
chapter_files = Rake::FileList[chapters.map{|c| "#{c}/#{c}.adoc"}]
slide_outfiles = Rake::FileList[chapters.map{|c| "html/#{c}.html"}]
fig_dirs = Rake::FileList[chapters.map{|c| "#{c}/figs"}]

# ajoute les fichiers html comme fichiers intermédiaire
CLEAN.include(slide_outfiles)
CLEAN << "html/index.html"
CLEAN << "html/figs"
# ajoute le répertoire html comme fichier de sortie
CLOBBER << "html"

desc "Génère la version livre et la version slides"
task :default => [:generate_book, :generate_slides]
# :default est un symbole Ruby
# task <cible> => <dépendances>

desc "Initialise le répertoire de destination des fichiers html"
task :init_html => %w[html/reveal.js html/figs]
directory "html/reveal.js" => "html" do |t|
    tn = t.source
    sh <<~HEREDOC
        wget -qO- https://github.com/hakimel/reveal.js/archive/#{REVEALJS_VERION}.tar.gz | \
        tar --transform 's/^reveal.js-#{REVEALJS_VERION}/reveal.js/' -xz -C #{tn}/
    HEREDOC
    sh <<~HEREDOC
        sed -e 's/  font-size: ..px;/  font-size: 24px;/' \
        #{tn}/reveal.js/css/theme/black.css > #{tn}/reveal.js/css/theme/blackmy.css
    HEREDOC
end

desc "Initialise le répertoire des images"
directory "html/figs" => "html" do
    mkdir_p "html/figs"
    cp Dir["figs/*.png", "figs/*.svg"], "html/figs"
    fig_dirs.each do |d|
        cp Dir["#{d}/*.png", "#{d}/*.svg"], "html/figs"
    end
end

# directory task pour créer le répertoire
directory "html"

desc "Génère la version livre du cours"
task :generate_book => [:init_html, "html/index.html"]
# file task
file "html/index.html" => %w[index.adoc] + chapter_files do
    sh "asciidoctor -r asciidoctor-diagram -D html/ index.adoc"
end

desc "Génère les slides"
task :generate_slides => [:init_html] + slide_outfiles

rule '.html' => ->(f){source_for_html(f)} do |t|
    chapter = t.name.ext('').sub(/^html\//, '')
    sh "asciidoctor-revealjs -r asciidoctor-diagram -D html/ #{chapter}/#{chapter}.adoc"
end

def source_for_html(html_file)
    chapter = html_file.ext('').sub(/^html\//, '')
    Rake::FileList["#{chapter}/*.adoc"]
end

